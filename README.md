# CFAutoScaler
An auto-scaler for Cloud Foundry PaaS

This auto-scaler consists of 2 components: scaling and monitoring service. The three packages of this project are: 
1. auto-scaling: performs scaling decisions, cloud foundry API calls 
2. data: persis the monitoring data into DB using JDBC 
3. logaggregation: parses and aggregate cloud foundry logs.
