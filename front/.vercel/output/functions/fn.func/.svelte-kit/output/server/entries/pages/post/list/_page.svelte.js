import { z as create_anchor, G as await_block, y as pop, H as ensure_array_like, E as attr, I as stringify, F as escape, t as push } from "../../../../chunks/index.js";
import { r as rq } from "../../../../chunks/rq.svelte.js";
function _page($$payload, $$props) {
  push(false);
  const anchor = create_anchor($$payload);
  $$payload.out += `<div><h1>Posts</h1> <ul>${anchor}`;
  await_block(
    rq.apiEndPoints().GET("/api/v1/posts"),
    () => {
      $$payload.out += `<li>loading...</li>`;
    },
    ({ data }) => {
      const anchor_1 = create_anchor($$payload);
      const each_array = ensure_array_like(data.data.items);
      $$payload.out += `${anchor_1}`;
      for (let $$index = 0; $$index < each_array.length; $$index++) {
        const post = each_array[$$index];
        const anchor_2 = create_anchor($$payload);
        $$payload.out += `${anchor_2}<li><a${attr("href", `/post/${stringify(post.id)}`, false)}>${escape(post.id)}. ${escape(post.title)}</a></li>${anchor_2}`;
      }
      $$payload.out += `${anchor_1}`;
    }
  );
  $$payload.out += `${anchor}</ul></div>`;
  pop();
}
export {
  _page as default
};
