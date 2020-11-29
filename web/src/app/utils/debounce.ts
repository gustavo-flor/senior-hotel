export default function debounce(method, wait) {
  let timeout;
  return function (...args) {
    clearTimeout(timeout);
    timeout = setTimeout(() => method.call(this, ...args), wait);
  }
}