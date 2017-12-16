# Document Management - Integration Tests
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/hmcts/document-management-integration-tests.svg?branch=master)](https://travis-ci.org/hmcts/document-management-integration-tests)
[![codecov](https://codecov.io/gh/hmcts/document-management-integration-tests/branch/master/graph/badge.svg)](https://codecov.io/gh/hmcts/document-management-integration-tests)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/efed494be39a41fb924eb43a4f3ef65a)](https://www.codacy.com/app/HMCTS/document-management-integration-tests) 
[![Known Vulnerabilities](https://snyk.io/test/github/hmcts/document-management-integration-tests/badge.svg)](https://snyk.io/test/github/hmcts/document-management-integration-tests)

This repo contains the tests for verifying the integration between document management
and its dependencies.

## Quickstart
### Clone Repo

```bash
#Clone repo
git clone https://github.com/hmcts/document-management-integration-test.git
cd document-management-integration-tests/
#Run Tests
./testme.sh
```

### Environment Variables
- DM_GW_BASE_URI - The base url of Document Management Api Gateway Web server
- DM_STORE_BASE_URI - The base url of Document Management Store App server
- IDAM_USER_BASE_URI - The base url of IDAM User server
- IDAM_S2S_BASE_URI - The base url of IDAM S2S server

### Run Integration Tests
```bash
./testme.sh
```
