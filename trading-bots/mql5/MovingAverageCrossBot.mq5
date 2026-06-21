//+------------------------------------------------------------------+
//| MovingAverageCrossBot.mq5                                        |
//| MQL5 Expert Advisor converted from a typical MQL4 MA cross bot.  |
//+------------------------------------------------------------------+
#property copyright "PortfolioManagement"
#property version   "1.00"
#property strict

#include <Trade/Trade.mqh>

input int      FastMAPeriod       = 12;
input int      SlowMAPeriod       = 26;
input double   LotSize            = 0.10;
input int      StopLossPoints     = 300;
input int      TakeProfitPoints   = 600;
input ulong    MagicNumber        = 20260621;
input ENUM_TIMEFRAMES SignalFrame = PERIOD_CURRENT;

CTrade trade;
int fastMaHandle = INVALID_HANDLE;
int slowMaHandle = INVALID_HANDLE;
datetime lastBarTime = 0;

int OnInit()
{
   if(FastMAPeriod <= 0 || SlowMAPeriod <= 0 || FastMAPeriod >= SlowMAPeriod)
   {
      Print("FastMAPeriod must be positive and lower than SlowMAPeriod.");
      return INIT_PARAMETERS_INCORRECT;
   }

   fastMaHandle = iMA(_Symbol, SignalFrame, FastMAPeriod, 0, MODE_EMA, PRICE_CLOSE);
   slowMaHandle = iMA(_Symbol, SignalFrame, SlowMAPeriod, 0, MODE_EMA, PRICE_CLOSE);

   if(fastMaHandle == INVALID_HANDLE || slowMaHandle == INVALID_HANDLE)
   {
      Print("Unable to create moving average indicator handles. Error: ", GetLastError());
      return INIT_FAILED;
   }

   trade.SetExpertMagicNumber(MagicNumber);
   trade.SetDeviationInPoints(20);
   return INIT_SUCCEEDED;
}

void OnDeinit(const int reason)
{
   if(fastMaHandle != INVALID_HANDLE)
      IndicatorRelease(fastMaHandle);

   if(slowMaHandle != INVALID_HANDLE)
      IndicatorRelease(slowMaHandle);
}

void OnTick()
{
   datetime currentBarTime = iTime(_Symbol, SignalFrame, 0);
   if(currentBarTime == 0 || currentBarTime == lastBarTime)
      return;

   lastBarTime = currentBarTime;

   double fastMa[3];
   double slowMa[3];
   ArraySetAsSeries(fastMa, true);
   ArraySetAsSeries(slowMa, true);

   if(CopyBuffer(fastMaHandle, 0, 0, 3, fastMa) != 3 || CopyBuffer(slowMaHandle, 0, 0, 3, slowMa) != 3)
   {
      Print("Unable to read moving average buffers. Error: ", GetLastError());
      return;
   }

   bool bullishCross = fastMa[1] > slowMa[1] && fastMa[2] <= slowMa[2];
   bool bearishCross = fastMa[1] < slowMa[1] && fastMa[2] >= slowMa[2];

   if(bullishCross)
   {
      ClosePositions(POSITION_TYPE_SELL);
      OpenPosition(ORDER_TYPE_BUY);
   }
   else if(bearishCross)
   {
      ClosePositions(POSITION_TYPE_BUY);
      OpenPosition(ORDER_TYPE_SELL);
   }
}

void OpenPosition(const ENUM_ORDER_TYPE orderType)
{
   if(HasPosition(orderType == ORDER_TYPE_BUY ? POSITION_TYPE_BUY : POSITION_TYPE_SELL))
      return;

   double price = orderType == ORDER_TYPE_BUY ? SymbolInfoDouble(_Symbol, SYMBOL_ASK) : SymbolInfoDouble(_Symbol, SYMBOL_BID);
   double stopLoss = 0.0;
   double takeProfit = 0.0;

   if(StopLossPoints > 0)
      stopLoss = orderType == ORDER_TYPE_BUY ? price - StopLossPoints * _Point : price + StopLossPoints * _Point;

   if(TakeProfitPoints > 0)
      takeProfit = orderType == ORDER_TYPE_BUY ? price + TakeProfitPoints * _Point : price - TakeProfitPoints * _Point;

   bool placed = orderType == ORDER_TYPE_BUY
      ? trade.Buy(LotSize, _Symbol, price, stopLoss, takeProfit, "MA cross buy")
      : trade.Sell(LotSize, _Symbol, price, stopLoss, takeProfit, "MA cross sell");

   if(!placed)
      Print("Order placement failed. Retcode: ", trade.ResultRetcode(), " - ", trade.ResultRetcodeDescription());
}

bool HasPosition(const ENUM_POSITION_TYPE positionType)
{
   for(int index = PositionsTotal() - 1; index >= 0; index--)
   {
      ulong ticket = PositionGetTicket(index);
      if(ticket == 0 || !PositionSelectByTicket(ticket))
         continue;

      if(PositionGetString(POSITION_SYMBOL) == _Symbol
         && PositionGetInteger(POSITION_MAGIC) == (long)MagicNumber
         && PositionGetInteger(POSITION_TYPE) == positionType)
      {
         return true;
      }
   }

   return false;
}

void ClosePositions(const ENUM_POSITION_TYPE positionType)
{
   for(int index = PositionsTotal() - 1; index >= 0; index--)
   {
      ulong ticket = PositionGetTicket(index);
      if(ticket == 0 || !PositionSelectByTicket(ticket))
         continue;

      if(PositionGetString(POSITION_SYMBOL) != _Symbol
         || PositionGetInteger(POSITION_MAGIC) != (long)MagicNumber
         || PositionGetInteger(POSITION_TYPE) != positionType)
      {
         continue;
      }

      if(!trade.PositionClose(ticket))
         Print("Unable to close position ", ticket, ". Retcode: ", trade.ResultRetcodeDescription());
   }
}
