import createClient from "openapi-fetch";
import toastr from "toastr";
function client_method(key) {
  {
    if (key === "before_navigate" || key === "after_navigate" || key === "on_navigate" || key === "push_state" || key === "replace_state") {
      return () => {
      };
    } else {
      const name_lookup = {
        disable_scroll_handling: "disableScrollHandling",
        preload_data: "preloadData",
        preload_code: "preloadCode",
        invalidate_all: "invalidateAll"
      };
      return () => {
        throw new Error(`Cannot call ${name_lookup[key] ?? key}(...) on the server`);
      };
    }
  }
}
const goto = /* @__PURE__ */ client_method("goto");
class Rq {
  member;
  shouldLogoutPagePaths = ["/member/login", "/member/join"];
  shouldLoginPagePaths = ["/post/myList"];
  constructor() {
    let id = 0;
    let username = "";
    let createDate = "";
    let modifyDate = "";
    let authorities = [];
    this.member = {
      get id() {
        return id;
      },
      set id(value) {
        id = value;
      },
      get createDate() {
        return createDate;
      },
      set createDate(value) {
        createDate = value;
      },
      get modifyDate() {
        return modifyDate;
      },
      set modifyDate(value) {
        modifyDate = value;
      },
      get username() {
        return username;
      },
      set username(value) {
        username = value;
      },
      get authorities() {
        return authorities;
      },
      set authorities(value) {
        authorities = value;
      }
    };
  }
  apiEndPoints() {
    return createClient({
      baseUrl: "https://api.medium.oa.gg",
      credentials: "include"
    });
  }
  msgInfo(message) {
    toastr.info(message);
  }
  msgError(message) {
    toastr.error(message);
  }
  goTo(url) {
    goto(url);
  }
  replace(url) {
    goto(url, { replaceState: true });
  }
  setLogined(member) {
    this.member.id = member.id;
    this.member.createDate = member.createDate;
    this.member.modifyDate = member.modifyDate;
    this.member.username = member.username;
    this.member.authorities = member.authorities;
  }
  setLogout() {
    this.member.id = 0;
    this.member.createDate = "";
    this.member.modifyDate = "";
    this.member.username = "";
    this.member.authorities = [];
  }
  isLogin() {
    return this.member.id !== 0;
  }
  isLogout() {
    return !this.isLogin();
  }
  async initAuth() {
    const { data } = await this.apiEndPoints().GET("/api/v1/members/me");
    if (data) {
      this.setLogined(data.data.item);
    }
    this.checkAuth();
  }
  async logout() {
    await this.apiEndPoints().POST("/api/v1/members/logout");
    this.setLogout();
    this.goToMain();
  }
  shouldLogoutPage() {
    return this.shouldLogoutPagePaths.includes(window.location.pathname);
  }
  shouldLoginPage() {
    return this.shouldLoginPagePaths.includes(window.location.pathname);
  }
  checkAuth() {
    if (this.isLogin()) {
      const needToGoMainPage = this.shouldLogoutPage();
      if (needToGoMainPage) {
        this.goToMain();
      }
    } else {
      const needToGoLoginPage = this.shouldLoginPage();
      if (needToGoLoginPage) {
        this.goToLoginPage();
      }
    }
  }
  goToMain() {
    this.goTo("/");
  }
  goToEditPage() {
    this.effect(async () => {
      const { data } = await this.apiEndPoints().POST("/api/v1/posts/temp");
      if (data) {
        this.goTo(`/post/edit/${data.data.item.id}`);
      }
    });
  }
  goToLoginPage() {
    this.goTo("/member/login");
  }
  effect(fn) {
  }
  getKakaoLoginUrl() {
    return `${"https://api.medium.oa.gg"}/member/socialLogin/kakao?redirectUrl=${encodeURIComponent("https://medium.oa.gg")}/member/socialLoginCallback?provierTypeCode=kakao`;
  }
}
const rq = new Rq();
export {
  rq as r
};
