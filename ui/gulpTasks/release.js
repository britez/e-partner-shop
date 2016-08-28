var gulp = require('gulp');
var packageJson = require('../package.json');
var cache = require('gulp-cached');
var plumber = require('gulp-plumber');
var run = require('gulp-run');
var runSequence = require('run-sequence');
var release = false;

/**
 * Publica en npm
 */
gulp.task('publish', function() {
  gulp.src(
    packageJson.moduleConfig.output+'**/*',
    {base:'dist'})
    .pipe(cache('publish'))
    .pipe(plumber())
    .pipe(gulp.dest(packageJson.moduleConfig.build));
  return run('npm publish --registry='+packageJson.publishConfig.registry)
    .exec();
});

/**
 * Borra todo, compila los js
 */
gulp.task('release', function(callback) {
  release = true;
  runSequence('clean', 'compile-release', 'publish', 'clean-release', callback);
});