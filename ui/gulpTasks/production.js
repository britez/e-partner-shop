var gulp = require('gulp');
var packageJson = require('../package.json');
var release = false;
var jspm = require('jspm');
var plumber = require('gulp-plumber');
var runSequence = require('run-sequence');
var replace = require('gulp-replace-task');
/**
 * Production flow
 */
gulp.task('build-release', function(callback) {
  return runSequence('move', callback);
});

/**
 * @Deprecated
 * Production flow
 */
gulp.task('bundle-sfx', function() {
  var jsMainPath =  packageJson.moduleConfig.output+packageJson.moduleConfig.mainJs;
  if (release) {
    jsMainPath = packageJson.moduleConfig.output+packageJson.main;
  }
  return jspm.bundleSFX(jsMainPath, packageJson.moduleConfig.output+'build.js',
    { runtime: false }
  );
});

/**
 * Production flow
 */
gulp.task('production:bundle:main', function() {
  var deps = packageJson.jspm.dependencies;

  var moduleList = packageJson.moduleConfig.output + packageJson.main + ' - ' + Object.keys(deps).join(' - ');

  console.log('dependencias '+moduleList)

  return jspm.bundle(moduleList, packageJson.moduleConfig.output + 'bundle.js',
    { mangle: false, minify:false, sourceMaps: true, inject: false }).then(function() {
  });

});

/**
 * Production flow
 */
gulp.task('production:bundle:vendor', function() {
  var deps = packageJson.jspm.dependencies;

  var vendors = [];

  for(var key in deps) {
    if (deps[key].indexOf('npm-redbee') === -1) {
      vendors.push(key);
    }

  }
  var moduleList = vendors.join(' + ');

  console.log('dependencias '+moduleList)

  return jspm.bundle(moduleList, packageJson.moduleConfig.output + 'vendors.js',
    { mangle: false, minify:false, sourceMaps: true, inject: false }).then(function() {
    });

});

/**
 * Production flow
 */
gulp.task('production:bundle:localdeps', function() {
  var deps = packageJson.jspm.dependencies;

  var vendors = [];

  for(var key in deps) {
    if (deps[key].indexOf('npm-redbee') !== -1) {
      vendors.push(key);
    }

  }
  var moduleList = vendors.join(' + ');

  console.log('dependencias '+moduleList)

  return jspm.bundle(moduleList, packageJson.moduleConfig.output + 'dependencies.js',
    { mangle: false, minify:false, sourceMaps: true, inject: false }).then(function() {
    });

});

gulp.task('production:cdnify:dependencies', function() {

  var dependencies = packageJson.jspm.dependencies;

  for( var key in dependencies) {

    if (dependencies[key].indexOf('npm-redbee') !== -1) {

      console.log(dependencies[key]);

      var route = '/cdn/cdn-'+dependencies[key].replace(/.*:(.*)\@.*/, '$1');
      var cdnDir = './jspm_packages/'+dependencies[key].replace('^','').replace(':','/');

      gulp.src(cdnDir + route + '/**/*',{base: cdnDir})
        .pipe(gulp.dest(packageJson.moduleConfig.output))

    }
  }

});

gulp.task('production:cdnify:local', function() {
  return gulp.src(['images/**/*', 'fonts/**/*'], {base: '.'})
    .pipe(gulp.dest(packageJson.moduleConfig.output + 'cdn/cdn-' + packageJson.name));
});


gulp.task('production:move', function() {
  //TODO:(mbritez) Ezequiel tiene que sacar las fuentes.
  var src = ['./jspm_packages/system.js', './jspm_packages/bower/material-design-iconic-font@2.1.2/dist/**/*.woff',
    './jspm_packages/bower/material-design-iconic-font@2.1.2/dist/**/*.ttf',
    './jspm_packages/bower/material-design-iconic-font@2.1.2/dist/**/*.woff2']

  return gulp.src(src,{base:'.'})
    .pipe(plumber())
    .pipe(gulp.dest(packageJson.moduleConfig.output));
});

gulp.task('production:replace', function() {

  return gulp.src(packageJson.moduleConfig.output+'index.html')
    .pipe(replace({
      usePrefix:false,
      patterns: [
        {
          match: '<!-- <script src="vendors.js"></script> -->',
          replacement: '<script src="vendors.js?bust={{date}}"></script>'
        },
        {
          match: '<!-- <script src="dependencies.js"></script> -->',
          replacement: '<script src="dependencies.js?bust={{date}}"></script>'
        },
        {
          match: '<!-- <script src="bundle.js"></script> -->',
          replacement: '<script src="bundle.js?bust={{date}}"></script>'
        },
        {
          match: '{{date}}',
          replacement: Math.round(new Date() / 1000)
        }
      ]
    }))
    .pipe(gulp.dest(packageJson.moduleConfig.output));
});

gulp.task('production:flow', function() {

  return runSequence(
    'clean',
    ['move', 'production:move', 'production:cdnify:dependencies', 'production:cdnify:local'],
    ['sass', 'html'],
    ['production:bundle:main', 'production:bundle:localdeps', 'production:bundle:vendor'],
    'production:replace'
  );

});

/**
 * Minifica las funestes
 */
gulp.task('minify', function() {
  return gulp.src(packageJson.moduleConfig.output+'/**/*.js')
    .pipe(cache('minify'))
    .pipe(plumber())
    .pipe(ngAnnotate())
    .pipe(stripCode
    ({
      start_comment: "start-to-remove-release",
      end_comment: "end-to-remove-release"
    })
  )
    .pipe(sourcemaps.init())
    .pipe(uglify())
    .pipe(sourcemaps.write("."))
    .pipe(gulp.dest('dist'));
});
