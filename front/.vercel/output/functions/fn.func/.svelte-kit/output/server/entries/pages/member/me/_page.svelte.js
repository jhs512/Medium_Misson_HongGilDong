import { F as escape, y as pop, t as push } from "../../../../chunks/index.js";
import { r as rq } from "../../../../chunks/rq.svelte.js";
function _page($$payload, $$props) {
  push(false);
  $$payload.out += `<div>username: ${escape(rq.member.username)} <br> createDate: ${escape(rq.member.createDate)}</div>`;
  pop();
}
export {
  _page as default
};
