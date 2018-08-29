package com.mobius.software.amqp.parser.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mobius.software.amqp.parser.test.negative.codes.TestNullCode;
import com.mobius.software.amqp.parser.test.negative.described.TestSourceExceptions;
import com.mobius.software.amqp.parser.test.negative.described.TestTargetExceptions;
import com.mobius.software.amqp.parser.test.negative.headers.*;
import com.mobius.software.amqp.parser.test.negative.types.TestUnwrapperExceptions;
import com.mobius.software.amqp.parser.test.negative.types.TestWrapperExceptions;

@RunWith(Suite.class)
@SuiteClasses(
{ TestNullCode.class, TestOpenExceptions.class, TestBeginExceptions.class, TestAttachExceptions.class, TestFlowExceptions.class, TestTransferExceptions.class, TestDispositionExceptions.class, TestDetachExceptions.class, TestDeliveryAnnotationsExceptions.class, TestSourceExceptions.class, TestTargetExceptions.class, TestInitExceptions.class, TestChallengeExceptions.class, TestResponseExceptions.class, TestOutcomeExceptions.class, TestWrapperExceptions.class, TestUnwrapperExceptions.class })
public class NegativeTests
{
}
