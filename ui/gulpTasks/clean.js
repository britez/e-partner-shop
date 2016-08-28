var gulp = require('gulp');
var packageJson = require('../package.json');
var vinylPaths = require('vinyl-paths');
var del = require('del');

/**
 * Borra directorio de dist. Aca van los compilados
 */
gulp.task('clean', function () {
  return gulp.src([packageJson.moduleConfig.output])
      .pipe(vinylPaths(del));
});

/**
 * Borra directorios de release
 */
gulp.task('clean-release', function() {
  return gulp.src([packageJson.moduleConfig.build])
      .pipe(vinylPaths(del));
});
