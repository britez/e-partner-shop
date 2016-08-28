var gulp = require('gulp');
var runSequence = require('run-sequence');
var packageJson = require('../package.json');
var changed = require('gulp-changed');
var cache = require('gulp-cached');
var plumber = require('gulp-plumber');
var sourcemaps = require("gulp-sourcemaps");
var babel = require('gulp-babel');
var ngAnnotate = require('gulp-ng-annotate');
var browserSync = require('browser-sync');

var compilerOptions = {
  modules: 'system',
  moduleIds: true,
  externalHelpers: true,
  comments: true,
  compact: false
};

/**
 * Hace el move y build de los js
 */
gulp.task('build', function(callback) {
  return runSequence(['move', 'es6'], callback);
});

/**
 * transpila los js a es5
 */
gulp.task('es6', function () {
  return gulp.src(packageJson.moduleConfig.source, {base:'.'})
    .pipe(cache('es6'))
    .pipe(plumber())
    .pipe(changed(packageJson.moduleConfig.output, { extension: '.js' }))
    .pipe(sourcemaps.init())
    .pipe(babel(compilerOptions))
    .pipe(ngAnnotate({
      sourceMap: true,
      gulpWarnings: false
    }))
    .pipe(sourcemaps.write("."))
    .pipe(gulp.dest(packageJson.moduleConfig.output))
    .pipe(browserSync.reload({ stream: true }));
});

