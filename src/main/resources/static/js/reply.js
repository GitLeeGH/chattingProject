//async 는 함수 선언시 사용 -> 해당 함수가 비동기 처리를 위한 함수라는 것을 명시
//await 는 async 함수 내에서 비동기 호출하는 부분에 사용
async function get1(bno) {

    const result = await axios.get(`/replies/list/${bno}`)

    // 화면에서 결과가 필요하다면 Axios 의 호출 결과를 반환받아야 한다
    // console.log(result)

    return result.data
}

// 댓글 리스트 불러오기
async function getList({bno, page, size, goLast}){

    const result = await axios.get(`/replies/list/${bno}`, {params : {page,size}})

    // 마지막 페이지를 계산 후 마지막 페이지 다시 호출
    if(goLast) {

        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}

// 댓글 등록
async function addReply(replyObj) {

    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}

// 댓글 조회
async function getReply(rno) {

    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

// 댓글 수정
async function modifyReply(replyObj) {

    const response = await axios.put(`/replies/${replyObj.rno}`,replyObj)
    return response.data
}

// 댓글 삭제
async  function removeReply(rno) {

    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}