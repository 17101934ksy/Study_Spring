const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  outputDir: '../backend/src/main/resources/static',
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:3000',
        ws: true,
        changeOrigin: true,
      }
    }
  }
});
