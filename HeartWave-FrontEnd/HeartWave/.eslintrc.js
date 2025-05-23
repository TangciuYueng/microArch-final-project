module.exports = {
  extends: [
    'plugin:vue/vue3-recommended',
    'eslint:recommended',
  ],
  rules: {
    'vue/max-attributes-per-line': ['error', {
      singleline: 5 //标签超出5个属性就会换行
    }],
    'vue/script-setup-uses-vars': 'error', // setup 语法糖校验
    'object-curly-spacing': ['error', 'always'], // 对象前后要加空格 { a: 1 }
    'array-bracket-spacing': ['error', 'always'], // 数组前后要加空格 [ 1, 2 ]
    'array-bracket-newline': ['error', { "minItems": 5}], // 数组超过五个值可以换行
    'arrow-spacing': "error", //箭头函数前后加空格 () => {}
    'vue/no-unsupported-features': ['error', { // 校验不支持的特性
      'version': "^3.0.0",
      'ignores': [],
    }]
  }
}