import { C as store_get, J as head, B as escape_text, D as unsubscribe_stores, y as pop, F as escape, t as push } from "../../../../chunks/index.js";
import { p as page } from "../../../../chunks/stores.js";
function _page($$payload, $$props) {
  push(false);
  const $$store_subs = {};
  const post = store_get($$store_subs, "$page", page).data.post;
  head($$payload, ($$payload2) => {
    $$payload2.title = "<title>";
    $$payload2.title += `글 ${escape(post.title)}</title>`;
  });
  $$payload.out += `<div><h1>${escape_text(post.title)}</h1> <p>${escape_text(post.body)}</p></div>`;
  unsubscribe_stores($$store_subs);
  pop();
}
export {
  _page as default
};
