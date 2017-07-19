## Website Monitoring Tool 

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1eff970353684ed58d115bb4db09e28d)](https://www.codacy.com/app/pavlo-plynko/web-monitor?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shcho-isle/web-monitor&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/shcho-isle/web-monitor.svg?branch=master)](https://travis-ci.org/shcho-isle/web-monitor)

The application monitors the state of external web applications by periodically pumping out pages according to given URLs, and monitoring response time, HTTP response code, response size in a certain range, and optionally - the presence of some substring in the response.

## Launching

- Build `war` file using maven

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