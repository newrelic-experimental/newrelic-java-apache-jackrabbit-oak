## Version: [v1.1.0](https://github.com/newrelic-experimental/newrelic-java-apache-jackrabbit-oak/releases/tag/v1.1.0) | Created: 2024-03-11
### Features
- Instrumented Commit and DocumentStore
- Merge branch 'main' into feat/instrument-oak-store-document
- Merge pull request #6 from newrelic-experimental/feat/instrument-oak-store-document

### Bug Fixes
- Added additional classes to oak-store-document and Version 1.12+
- Fixed build store document verifyInstrumentation

## Version: [v1.0.0](https://github.com/newrelic-experimental/newrelic-java-apache-jackrabbit-oak/releases/tag/v1.0.0) | Created: 2024-02-20
### Features
- Add instrumentation for oak-core
- Address PR comments
- Remove logger and use method to setMetricName
- Remove unused import
- Merged pull request #1 from newrelic-experimental/feat/instrument-oak-core
- Instrument oak-api blob provider and query engine
- Merged pull request #2 from newrelic-experimental/feat/instrument-oak-api
- Instrument the oak-blob package
- Merged pull request #3 from newrelic-experimental/feat/instrument-oak-blob
- Instrument the oak-segment-tar package
- Merged pull request #4 from newrelic-experimental/feat/instrument-oak-segment-tar
- Instrument oak-store-composite
- Merged pull request #5 from newrelic-experimental/feat/instrument-oak-store-composite

### Bug Fixes
- Factor out constants to fix IllegalAccessException

## Installation

To install:

1. Download the latest release jar files.
2. In the New Relic Java directory (the one containing newrelic.jar), create a directory named extensions if it does not already exist.
3. Copy the downloaded jars into the extensions directory.
4. Restart the application.   
