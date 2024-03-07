package org.test.trout.metrics;

public interface MetricsCollector {
	
	public void addCount(String metricName, int count);
	
	public void addTime(long time);
	
	public void dumpStats();

}
