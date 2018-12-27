# AMQP-parser

AMQP parser is a library designed for encoding and decoding of AMQP packets. It is written in Java. 
AMQP parser is developed by [Mobius Software](http://www.mobius-software.com).


## Getting Started

First you should clone AMQP parser. Then you should add the following lines within the <project> element of pom.xml file of 
your project:

```
<dependency>
  <groupId>com.mobius-software.amqp</groupId>
	<artifactId>amqp-parser</artifactId>
<version>1.0.4-SNAPSHOT</version>
</dependency>
```
Now you are able to start using AMQP parser.

# Examples

## Create, encode, decode message

```
try
		{
                            int channel = 0;
                            String clientId = "12345qwert";
                            int keepAlive = 60;
                            AMQPOpen open = AMQPOpen.builder().channel(channel).containerId(clientId).idleTimeout(keepAlive * 1000).build();
		
		            // Encode message
			    ByteBuf encoded = AMQPParser.encode(open);
		            // process encoded value...

			    // Decode message
			    AMQPHeader decoded = AMQPParser.decode(encoded);
			    // process decoded value...
		}
		catch (Exception e)
		{
			    e.printStackTrace();
			    fail();
		}

```

