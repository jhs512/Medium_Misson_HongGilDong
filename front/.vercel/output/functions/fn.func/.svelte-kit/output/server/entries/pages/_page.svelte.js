import { y as pop, t as push } from "../../chunks/index.js";
function _page($$payload, $$props) {
  push(false);
  $$payload.out += `<div class="bg-red-500 w-[100px] h-[100px]">123!!!!</div> <button class="btn btn-primary">버튼!!!</button> <i class="fa fa-user"></i>!!!`;
  pop();
}
export {
  _page as default
};
