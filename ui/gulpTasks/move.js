var gulp = require('gulp');
var release = false;
var cache = require('gulp-cached');
var plumber = require('gulp-plumber');
var packageJson = require('../package.json');

/**
 * Mueve todos los files a dist
 */
gulp.task('move', function() {
  var srcs = [
    'app/**/*.js',
    'app/**/*.json'
  ];
  if (!release) { //Si no esta en release se copia el index.html al dist
    srcs.push('./index.html');
    srcs.push('./config.js');
  }
  return gulp.src(
    srcs,
    {base:'.'})
    .pipe(cache('move'))
    .pipe(plumber())
    .pipe(gulp.dest(packageJson.moduleConfig.output));

});
