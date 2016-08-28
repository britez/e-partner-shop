#!/usr/bin/env bash
git config url."https://".insteadOf git:// && jspm registry create bower jspm-bower-endpoint -y && jspm config registries.npm-redbee.remote https://npm.jspm.io && jspm config registries.npm-redbee.registry http://repo.dev.redbee.io:80/content/repositories/npm-redbee/ && jspm config registries.npm-redbee.handler jspm-npm && jspm install
