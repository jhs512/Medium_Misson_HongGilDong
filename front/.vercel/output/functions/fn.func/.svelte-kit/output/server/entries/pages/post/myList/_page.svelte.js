import { z as create_anchor, H as ensure_array_like, E as attr, I as stringify, F as escape, y as pop, t as push } from "../../../../chunks/index.js";
import { r as rq } from "../../../../chunks/rq.svelte.js";
function _page($$payload, $$props) {
  push(true);
  let posts = [];
  rq.effect(async () => {
    const { data } = await rq.apiEndPoints().GET("/api/v1/posts/mine");
    if (data) {
      posts = data.data.items;
    }
  });
  const anchor = create_anchor($$payload);
  const each_array = ensure_array_like(posts);
  $$payload.out += `<div><h1>My Posts</h1> <ul>${anchor}`;
  for (let $$index = 0; $$index < each_array.length; $$index++) {
    const post = each_array[$$index];
    const anchor_1 = create_anchor($$payload);
    $$payload.out += `${anchor_1}<li><a${attr("href", `/post/${stringify(post.id)}`, false)}>${escape(post.id)}. ${escape(post.title)}</a></li>${anchor_1}`;
  }
  $$payload.out += `${anchor}</ul></div>`;
  pop();
}
export {
  _page as default
};
