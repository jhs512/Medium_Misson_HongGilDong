import { C as store_get, D as unsubscribe_stores, y as pop, t as push } from "../../../../chunks/index.js";
import { p as page } from "../../../../chunks/stores.js";
import { r as rq } from "../../../../chunks/rq.svelte.js";
function _page($$payload, $$props) {
  push(false);
  const $$store_subs = {};
  rq.effect(() => {
    const provierTypeCode = store_get($$store_subs, "$page", page).url.searchParams.get("provierTypeCode");
    console.log(provierTypeCode);
    rq.msgInfo("환영합니다.");
    rq.replace(`/`);
  });
  unsubscribe_stores($$store_subs);
  pop();
}
export {
  _page as default
};
