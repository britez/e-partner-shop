var gulp = require('gulp');
var packageJson = require('../package.json');
var runSequence = require('run-sequence');

/**
 * Borra dist, levanta server y hace watch de los files
 */
gulp.task('watch', function() {
  runSequence('clean', 'serve');
  var watcher = gulp.watch([packageJson.moduleConfig.source, packageJson.moduleConfig.sass, packageJson.moduleConfig.templates], ['compile']);
  watcher.on('change', function (event) {
    console.log('File ' + event.path + ' was ' + event.type + ', running tasks...');
  });
});
