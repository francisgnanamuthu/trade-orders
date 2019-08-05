package com.fcr.trade.orders

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([MakeATradeOrderSpec.class])
class RunStorySuite {
    private RunStorySuite() {
    }
}
