Vue.filter('number', function (value) {
  let s = value.toString();
  let s1 = s, s2 = '';
  let index = s.indexOf('.');
  if (index >= 0) {
    s1 = s.substring(0, index);
    s2 = s.substring(index);
  }
  let a = s1.split('').reverse();
  let temp = [];  
  for (let i = 0; i < a.length; ++i) {
    if (i > 0 && i % 3 == 0) temp.push(',');
    temp.push(a[i]);
  }
  return temp.reverse().join('') + s2;
});
