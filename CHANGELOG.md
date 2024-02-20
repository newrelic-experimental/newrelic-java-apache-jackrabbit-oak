## Version: [v1.0.0](https://github.com/newrelic-experimental/newrelic-java-apache-jackrabbit-oak/releases/tag/v1.0.0) | Created: 2024-02-20
### Features
- Add instrumentation for oak-core
- Address PR comments
- Remove logger and use method to setMetricName
- Remove unused import
- Erge pull request #1 from newrelic-experimental/feat/instrument-oak-core
- Instrument oak-api blob provider and query engine
- Erge pull request #2 from newrelic-experimental/feat/instrument-oak-api
- Instrument the oak-blob package
- Erge pull request #3 from newrelic-experimental/feat/instrument-oak-blob
- Instrument the oak-segment-tar package
- Erge pull request #4 from newrelic-experimental/feat/instrument-oak-segment-tar
- Instrument oak-store-composite
- Erge pull request #5 from newrelic-experimental/feat/instrument-oak-store-composite

### Bug Fixes
- Factor out constants to fix IllegalAccessException

## Installation

To install:

1. Download the latest release jar files.
2. In the New Relic Java directory (the one containing newrelic.jar), create a directory named extensions if it does not already exist.
3. Copy the downloaded jars into the extensions directory.
4. Restart the application.   
