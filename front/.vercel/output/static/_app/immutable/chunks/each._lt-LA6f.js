import{k as x,r as C,l as V,E as O,m as Y,n as M,o as T,q as w,v as q,w as A,x as F,y as R,z as G,A as K,s as D}from"./runtime.GVHMQo70.js";import{y as U,z as y,A as b,B as X,C as L,D as j,E as J,F as S,G as P,H as Q,I as W}from"./render.kzWBI0oE.js";import{S as B}from"./disclose-version.TT3aB54g.js";function Z(){}function $(e,i,a,s,n,f,d){const g=(a&A)!==0,l=P(a,e);let h=null;U(e,g);let v,E=null,m=null;l.r=t=>{const o=h,c=o.s;c.add(t),t.f(()=>{c.delete(t),c.size===0&&o.e!==null&&(o.d!==null&&(y(o.d),o.d=null),x(o.e),o.e=null)})};const r=()=>{const t={d:null,e:null,s:new Set,p:h},o=C(()=>{const c=l.d;c!==null&&(y(c),l.d=null);let _=l.a;(l.f&A)!==0&&(_=Q(),l.a.appendChild(_)),f(_),t.d=l.d,l.d=null},l,!0);t.e=o,h=t},p=t=>{const o=t.f,c=(o&A)!==0,_=t.a;d(v,t,_,c,n,o,!0,E)},u=C(()=>{const t=i();v=V(t)?t:t==null?[]:Array.from(t),s!==null?E=v.map(s):a&O||v.map(Z);const o=v.length;if(f!==null){if(o===0){if(l.v.length!==0||m===null){p(l),r();return}}else if(l.v.length===0&&h!==null){const c=h,_=c.s;_.size===0?c.d!==null&&(y(c.d),c.d=null):b(_,"out")}}m!==null&&F(m)},l,!1);m=C(p,l,!0),Y(u,()=>{const t=l.f,o=l.a,c=(t&A)!==0;let _=h;for(;_!==null;){const I=_.d;I!==null&&y(I);const H=_.e;H!==null&&x(H),_=_.p}d([],l,o,c,n,t,!1,E),x(m)}),l.e=u}function re(e,i,a,s,n){$(e,i,a,null,s,n,k)}function k(e,i,a,s,n,f,d){var g=B in e&&e[B].i,l=i.v,h=i.s;g&&(f&=~T);var v=l.length,E=e.length,m=Math.max(v,E),r=0,p,u;if(h.length!==0&&ne(h),E===0)for(p=[],s&&v!==0&&X(a);r<m;)u=l[r++],z(u,h,d,s);else{var t;if(p=Array(E),L!==null)for(var o=L[0];r<m;r++){t=g?M(e,r):e[r];var c=j(o);J(c),o=c.at(-1).nextSibling.nextSibling,u=N(t,null,r,n,f),p[r]=u}else for(;r<m;r++)r>=v?(t=g?M(e,r):e[r],u=N(t,null,r,n,f),p[r]=u,ee(u,a,s,null)):r>=E?(u=l[r],z(u,h,d)):(t=e[r],u=l[r],p[r]=u,le(u,t,r,f))}i.v=p}function ee(e,i,a,s){var n=e.d;return s===null?a?S(n,i,null):S(n,i.parentNode,i):S(n,null,s)}function ne(e){var i=e.length;if(i>0){for(var a=0,s,n;a<i;a++)s=e[a],n=s.r,n!==null&&(s.r=null,z(s,null,!1));e.length=0}}function le(e,i,a,s){s&T?w(e.v,i):q(e.v)&&(e.v.o[e.v.p]=i);const n=e.s,f=(s&R)!==0,d=e.a;n!==null&&s&O&&d!==null&&d(e,n,a,f),f?w(e.i,a):e.i=a}function z(e,i,a,s=!1){const n=e.s;if(a&&n!==null){for(let d of n)d.r==="key"&&n.delete(d);if(n.size===0)e.s=null;else{b(n,"out"),i!==null&&i.push(e);return}}const f=e.d;!s&&f!==null&&y(f),x(e.e)}function N(e,i,a,s,n){const d=(n&T)===0?e:n&G?D(e):K(e),g=n&R?D(a):a,l=W(d,g,i),h=C(v=>{s(null,v.v,v.i)},l,!0);return l.e=h,l}export{re as e};
