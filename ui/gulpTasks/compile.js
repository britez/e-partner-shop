var gulp = require('gulp');
var runSequence = require('run-sequence');
var packageJson = require('../package.json');
var browserSync = require('browser-sync');
var changed = require('gulp-changed');
var cache = require('gulp-cached');
var plumber = require('gulp-plumber');
var sourcemaps = require("gulp-sourcemaps");
var sass = require('gulp-sass');
var minifyHTML = require('gulp-minify-html');
var templateCache = require('gulp-angular-templatecache');
var img64 = require('gulp-img64');
var uglify = require('gulp-uglify');
var cdnify = require('gulp-cdnify');

/**
 * To be defined
 */
gulp.task('compile-release', function(callback) {
  return runSequence('sass', 'html', 'copy:assets', 'build-release', callback);
});

/**
 * Compila saas, html, hace el build de los js, corre metricas de codigo js y reload de browserSync
 */
gulp.task('compile', function(callback) {
  return runSequence(['sass', 'html', 'copy:assets', 'build', 'lint'], 'reload', callback);
});

/**
 * Recarga browser
 */
gulp.task('reload', function() {
  return browserSync.reload();
});

/**
 * Compila sass a css
 */
gulp.task('sass', function() {
  return gulp.src(packageJson.moduleConfig.sass)
    .pipe(cache('sass'))
    .pipe(plumber())
    .pipe(changed(packageJson.moduleConfig.output, {extension: '.css'}))
    .pipe(sourcemaps.init())
    .pipe(sass({
      outputStyle: 'compressed'
    }))
    .pipe(cdnify(
      {
        base: '.',
        rewriter: function(url, process) {
          if (/eot]ttf|woff|woff2/.test(url)) {
            return '/cdn/cdn-'+packageJson.name+'/fonts/' + url.replace('../fonts/', '');
          } else if (/(png|jpg|gif)$/.test(url)) {
            return '/cdn/cdn-'+packageJson.name+'/images/' + url.replace('../images/','');
          } else {
            return process(url);
          }
        }
      }
    )
  )
    .pipe(sourcemaps.write("."))
    .pipe(gulp.dest(packageJson.moduleConfig.output+'styles/'));
});

/**
 * Compila los html en js
 */
gulp.task('html', function() {
  return gulp.src(packageJson.moduleConfig.templates)
    .pipe(plumber())
    .pipe(cdnify(
      {
        html: {
          'img[src]': 'src'
        },
        base: '.',
        rewriter: function(url, process) {
          if (/eot]ttf|woff|woff2/.test(url)) {
            return '/cdn/cdn-'+packageJson.name+'/fonts/' + url.replace('../fonts/', '');
          } else if (/(png|jpg|gif)$/.test(url)) {
            return '/cdn/cdn-'+packageJson.name+'/images/' + url.replace('../images/','');
          } else {
            return process(url);
          }
        }
      }

    ))
    .pipe(minifyHTML({spare:true}))
    .pipe(templateCache
      (
        {
          standalone:true,
          module:packageJson.name+'-templates'
        }
      )
    )
    .pipe(uglify())
    .pipe(gulp.dest(packageJson.moduleConfig.output+'app/'));
});

gulp.task('copy:assets', function() {

  return gulp.src(['images/**/*', 'fonts/**/*'], {base:'.'})
    .pipe(gulp.dest(packageJson.moduleConfig.output+'/cdn/cdn-'+packageJson.name));
});