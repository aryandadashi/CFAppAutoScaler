# CFAutoScaler
An auto-scaler for Cloud Foundry PaaS based on CPU metric. As a part of [Cloudwave project](http://cloudwave-fp7.eu/), this auto-scaler was developed to demonstrate the Industrial Exploitation of the Cloudwave stack that consists of **Execution Analytics, Coordinated Adaptation and Feedback Driven Development**. This auto-scaler consists of **monitoring and scaling services**. The monitoring service implements the concepts of execution acalytics and the the scaling service exhibits the concepts of Coordinated Adaptation and Feedback Driven Development.

The three packages of this project are: 

1. auto-scaling: performs scaling decisions, cloud foundry API calls 
2. data: persists the monitoring data into PostgreSQL using JDBC 
3. logaggregation: parses and aggregate cloud foundry logs.

The following values need to be configured as required in the properties file before running the auto-scaler:
```
app_name=your_application_name
min_instances=minimum_number_of_instances
max_instances=maximum_number_of_instances
cool_down_interval=Interval_in_milliseconds (atlease 60000)
min_cpu_threshold=Minimum_CPUMetric_Threshold
max_cpu_threshold=Maximum_CPUMetric_Threshold
cf_api=Cloud_Foundry_API
cf_username=Cloud_Foundry_Username
cf_password=Cloud_Foundry_Password
```
After this configuration, the auto-scaler can be started by running the Monitoring Service which in turn invokes the scaling service. The application configured will now scale out/scale in whenever the CPU utilization crosses the thresholds configured.
