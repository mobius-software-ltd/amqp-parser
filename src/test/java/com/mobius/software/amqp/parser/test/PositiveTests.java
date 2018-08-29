package com.mobius.software.amqp.parser.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mobius.software.amqp.parser.test.positive.codes.*;
import com.mobius.software.amqp.parser.test.positive.constructor.TestDescribedConstructor;
import com.mobius.software.amqp.parser.test.positive.constructor.TestSimpleConstructor;
import com.mobius.software.amqp.parser.test.positive.described.*;
import com.mobius.software.amqp.parser.test.positive.headers.*;
import com.mobius.software.amqp.parser.test.positive.parser.TestParser;
import com.mobius.software.amqp.parser.test.positive.sections.*;
import com.mobius.software.amqp.parser.test.positive.tlv.TestArrayTLV;
import com.mobius.software.amqp.parser.test.positive.tlv.TestCompoundTLV;
import com.mobius.software.amqp.parser.test.positive.tlv.TestFixedTLV;
import com.mobius.software.amqp.parser.test.positive.tlv.TestVariableTLV;
import com.mobius.software.amqp.parser.test.positive.wrappers.TestDecimal;
import com.mobius.software.amqp.parser.test.positive.wrappers.TestMessageID;
import com.mobius.software.amqp.parser.test.positive.wrappers.TestSymbol;

@RunWith(Suite.class)
@SuiteClasses(
{ TestParser.class, TestFixedTLV.class, TestVariableTLV.class, TestCompoundTLV.class, TestArrayTLV.class, TestSimpleConstructor.class, TestDescribedConstructor.class, TestAccepted.class, TestModified.class, TestReceived.class, TestRejected.class, TestReleased.class, TestError.class, TestTarget.class, TestSource.class, TestProtoheader.class, TestPing.class, TestChallenge.class, TestInit.class, TestMechanisms.class, TestOutcome.class, TestResponse.class, TestAttach.class, TestBegin.class, TestClose.class, TestDetach.class, TestDisposition.class, TestEnd.class, TestFlow.class, TestOpen.class, TestTransfer.class, TestData.class, TestProperties.class, TestValue.class, TestFooter.class, TestSequence.class, TestApplicationProperties.class, TestDeliveryAnnotations.class, TestMessageAnnotations.class, TestMessageHeader.class, TestSymbol.class, TestMessageID.class, TestDecimal.class, TestArgumentCodes.class, TestDistributionModes.class, TestDurabilityCodes.class, TestErrorCodes.class, TestTerminusExpiryPolicyCodes.class, TestFieldCodes.class, TestOutcomeCodes.class, TestReceiveCodes.class, TestRoleCodes.class, TestSectionCodes.class, TestSendCodes.class, TestStateCodes.class, TestLifeTimePolicyCodes.class })
public class PositiveTests
{

}
