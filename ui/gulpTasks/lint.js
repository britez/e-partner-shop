var gulp = require('gulp');
var jshint = require('gulp-jshint');
var stylish = require('jshint-stylish');
var packageJson = require('../package.json');

/**
 * chequea el codigo javascript
 */
gulp.task('lint', function () {
  return gulp.src(packageJson.moduleConfig.source)
    .pipe(jshint({ "esnext": true, "globalstrict": true, "laxcomma": true }))
    .pipe(jshint.reporter(stylish));
});
