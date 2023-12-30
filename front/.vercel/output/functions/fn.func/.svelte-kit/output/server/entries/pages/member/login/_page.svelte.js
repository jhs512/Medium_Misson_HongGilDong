import { E as attr, y as pop, t as push } from "../../../../chunks/index.js";
import { r as rq } from "../../../../chunks/rq.svelte.js";
function _page($$payload, $$props) {
  push(false);
  $$payload.out += `<form><input type="text" name="username" placeholder="Username"> <input type="password" name="password" placeholder="Password"> <button type="submit">Login</button> <a${attr("href", rq.getKakaoLoginUrl(), false)}>카카오로그인</a></form>`;
  pop();
}
export {
  _page as default
};
