function getUrlParams(urlString) {
    // 평문 쿼리 문자열을 추출합니다.
    const url = new URL(urlString);
    // URL의 쿼리 문자열 이후로 모든 키와 값을 디코딩합니다.
    const queryParams = new URLSearchParams(url.search);
    // 객체를 만들어 줍니다.
    const params = {};

    for (let [key, value] of queryParams.entries()) {
        // 객체에 키와 값을 추가합니다.
        params[key] = value;
    }

    return params;
}