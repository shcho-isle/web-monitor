Website Monitoring Tool 
=======================
- Run `maven-install` script to compile application and build `war` file.

- Path to URL properties files: `/src/main/resources/urls/`

- URL properties file example:
```properties
    # monitoring period in seconds
    config.monitoringPeriod=14
    
    urlConfig.url=http://www.starwars.com/
    
    # warning and critical limits for response time in miliseconds
    urlConfig.warningTime=300
    urlConfig.criticalTime=400
    
    urlConfig.responseCode=200
    
    # min and max limits for response size in bytes
    urlConfig.minResponseSize=600000
    urlConfig.maxResponseSize=700000
    
    # optional parameter. Can be empty or absent.
    urlConfig.subString=Children’s Online Privacy Policy
```
    
- Application's model structure:

![alt text](https://github.com/shcho-isle/web-monitor/blob/master/diagram.png)