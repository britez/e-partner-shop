var gulp = require('gulp');
var Karma = require('karma').Server;
var sonar = require('gulp-sonar');
var packageJson = require('../package.json');

gulp.task('test', function (done) {
  new Karma({
    configFile: __dirname + '/../karma.conf.js',
    singleRun: true
  }, done).start();
});

/**
 * Watch for file changes and re-run tests on each change
 */
gulp.task('tdd', function (done) {
  new Karma({
    configFile: __dirname + '/../karma.conf.js'
  }, done).start();
});

/**
 * Ejecuta SonarRunner
 */
gulp.task('sonar', function () {
  var options = {
    sonar: {
      host: {
        url: 'http://argalephdockerci2:9997'
      },
      jdbc: {
        url: 'jdbc:mysql://argalephdockerci2:3307/sonar',
        username: 'sonar',
        password: '123qwe'
      },
      projectKey: packageJson.name,
      projectName: packageJson.description,
      projectVersion: packageJson.version,
      // comma-delimited string of source directories
      sources: 'app',
      language: 'js',
      sourceEncoding: 'UTF-8',
      javascript: {
        lcov: {
          reportPath: 'reports/coverage/lcov.info'
        }
      }
    }
  };
  return gulp.src('thisFileDoesNotExist.js', {read:false})
    .pipe(sonar(options))
});
