package wx.log4j2;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.util.JournalLogger;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager.Log4jMarker;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class pub

{
	// ---( internal utility methods )---

	final static pub _instance = new pub();

	static pub _newInstance() { return new pub(); }

	static pub _cast(Object o) { return (pub)o; }

	// ---( server methods )---




	public static final void addLog4j2Configuration (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addLog4j2Configuration)>> ---
		// @sigtype java 3.5
		// [i] field:0:required configFilePathname
		IDataMap pipeMap = new IDataMap(pipeline);
		
		String configFilePathname = pipeMap.getAsString("configFilePathname");
		String name = configFilePathname.replace(".", "").replace("/", ""). replace("\\", "");
		
		File configFile = new File(configFilePathname);
		
		if (configFile.exists() && configFile.canRead()) {
			try {
		    	LoggerContext context = new LoggerContext(name);
		    	
		    	context.setConfigLocation(configFile.toURI());
		    	
		    	Map<String, LoggerConfig> loggers = context.getConfiguration().getLoggers();
		    	Set<String> entries = loggers.keySet();
		    	for (String entry : entries) {
					loggerContextsMap.put(entry, context);
					Logger logger = context.getLogger(entry);
					loggerMap.put(entry, logger);
					logInternally("Logger " + entry + " initialized", Level.INFO);
				}
		    	logInternally("Config file " + configFilePathname + " initialized", Level.INFO);
			} catch (Exception e) {
				logInternally("Initialization failed for " + name +": " + e.getLocalizedMessage(), Level.ERROR);
			}
		} else {
			logInternally("Config file " + configFilePathname + " does not exist", Level.ERROR);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void closeLoggerContexts (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(closeLoggerContexts)>> ---
		// @sigtype java 3.5
		Set<Entry<String, LoggerContext>> loggerContexts = loggerContextsMap.entrySet();
		
		for (Entry<String, LoggerContext> entry : loggerContexts) {
			LoggerContext loggerContext = entry.getValue();
			loggerContext.close();
			LogManager.shutdown(loggerContext);
		}
		loggerContextsMap.clear();
		loggerMap.clear();
		
		LogManager.shutdown();
			
		// --- <<IS-END>> ---

                
	}



	public static final void log (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(log)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional message
		// [i] field:0:optional function
		// [i] field:0:optional level {"Fatal","Error","Warn","Info","Debug","Trace","Off"}
		IDataMap pipeMap = new IDataMap(pipeline);
		
		String message = pipeMap.getAsString("message");
		String function = pipeMap.getAsString("function");
		String strLevel = pipeMap.getAsString("level");
		
		Level level;
		if (strLevel != null) {
			level = Level.getLevel(strLevel);
		} else {
			// Default: INFO
			level = Level.INFO;
		}
		
		logImpl(message, function, level);
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static final String LOG_FUNCTION = "WxLog4j2";
	private static ConcurrentHashMap<String, LoggerContext> loggerContextsMap = new ConcurrentHashMap<String, LoggerContext>();
	private static ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
	
	private static void debugLogError(String message) {
	    JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.ERROR, LOG_FUNCTION, message);
	}
	
	private static void debugLogInfo(String message) {
	    JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, LOG_FUNCTION, message);
	}
	
	private static void debugLogDebug(String message) {
	    JournalLogger.log(4,  JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, LOG_FUNCTION, message);
	}
	
	private static void logImpl(String message, String function, Level level) {
		Logger logger;
		if (function == null) {
			function = LOG_FUNCTION;
		}
		logger = loggerMap.get(function);
		if (logger != null) {
			logger.log(level, message);
		} else {
			if (function.equals(LOG_FUNCTION)) {
				debugLogError("Logger for " + function + " not initialized!");
			} else {
				logInternally("Logger for " + function + " not initialized!", Level.ERROR);
			}
		}
	}
	
	
	private static void logInternally(String message, Level level) {
		logImpl(message, "WxLog4j2", level);
	}
	
	
	private static String getDiff(long start) {
		return String.valueOf(System.nanoTime() - start) + ", ";
	}
	
		
		
	// --- <<IS-END-SHARED>> ---
}

