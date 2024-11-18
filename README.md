<h1>WxLog4j2</h1>

Logging from webMethods IntegrationServer packages into custom logs using log4j2

<h2>Prerequisites</h2>

None

<h2>Installation</h2>

Normal deployment. The startup will create and write into a file ./logs/WxLog4j2.log

<h2>Usage</h2>

Create a file ./config/log4j2.xml like in WxLog4j2Demo:

<pre><code>
<?xml version="1.0" encoding="UTF-8"?>
<Configuration packages="wx.log4j2">
	<Appenders>
		<WxLog4j2RollingFile name="WxLog4j2Demo" fileName="./logs/WxLog4j2Demo.log" />
		<WxLog4j2RollingFile name="WxLog4j2Demo-Custom" fileName="./logs/WxLog4j2Demo-Custom.log">
			<Policies>
				<SizeBasedTriggeringPolicy size="10KB"/>
			</Policies>
			<DefaultRolloverStrategy max="3"/>
		</WxLog4j2RollingFile>
	</Appenders>
	<Loggers>
		<Logger name="WxLog4j2Demo" level="info" additivity="false">
			<AppenderRef ref="WxLog4j2Demo" />
		</Logger>
		<Logger name="WxLog4j2Demo-Custom" level="trace" additivity="false">
			<AppenderRef ref="WxLog4j2Demo-Custom"/>
		</Logger>
	</Loggers>
</Configuration>
</code></pre>

You can simple use the predefined appender WxLog4j2RollingFile or any other appender Log4j2 is providing.

Run
<pre><code>
wx.log4j2.pub:addLog4j2Configuration
</code></pre>
from your package, e.g. from startup. It will register you log4j2.xml

Invoke
<pre><code>
wx.log4j2.pub:log
</code></pre>
from your code and log into your logfile by using the input "function" in order to define your Logger like "WxLog4j2Demo"