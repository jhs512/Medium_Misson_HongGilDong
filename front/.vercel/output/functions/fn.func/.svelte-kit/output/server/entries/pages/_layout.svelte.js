import { A as untrack, z as create_anchor, B as escape_text, x as bind_props, y as pop, t as push } from "../../chunks/index.js";
import { r as rq } from "../../chunks/rq.svelte.js";
function _layout($$payload, $$props) {
  push(true);
  const { children } = $$props;
  rq.effect(async () => {
    untrack(() => {
      rq.initAuth();
    });
  });
  const anchor = create_anchor($$payload);
  const anchor_1 = create_anchor($$payload);
  const anchor_2 = create_anchor($$payload);
  $$payload.out += `<header class="flex"><ul class="flex gap-2"><li><a href="/" class="btn btn-link">홈</a></li> <li><a href="/post/list" class="btn btn-link">글</a></li> ${anchor}`;
  if (rq.isLogout()) {
    $$payload.out += "<!--ssr:if:true-->";
    $$payload.out += `<li><a href="/member/login" class="btn btn-link">로그인</a></li>`;
  } else {
    $$payload.out += "<!--ssr:if:false-->";
  }
  $$payload.out += `${anchor} ${anchor_1}`;
  if (rq.isLogin()) {
    $$payload.out += "<!--ssr:if:true-->";
    $$payload.out += `<li><button class="btn btn-link">글 작성</button></li> <li><a href="/post/myList" class="btn btn-link">내글</a></li> <li><button class="btn btn-link">로그아웃</button></li>`;
  } else {
    $$payload.out += "<!--ssr:if:false-->";
  }
  $$payload.out += `${anchor_1}</ul> <div class="flex-1"></div> <div><a href="/member/me" class="btn btn-link">${escape_text(rq.member.username)}</a></div></header> <main>${anchor_2}`;
  children($$payload);
  $$payload.out += `${anchor_2}</main>`;
  bind_props($$props, { children });
  pop();
}
export {
  _layout as default
};
