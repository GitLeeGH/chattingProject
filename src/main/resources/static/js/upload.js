// 서버 파일 업로드

async function uploadToServer (formObj) {

    console.log("upload to server ..............")
    console.log(formObj)

    const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        processData:false,
        contentType:false,
        headers: {
            'Content-Type' : 'multipart/form-data',
        },
    });

    return response.data
}

//첨부파일 삭제
function removeFile(uuid, fileName, obj) {

    console.log(uuid)
    console.log(fileName)
    console.log(obj)

    const targetDiv = obj.closest(".card")

    removeFileToServer(uuid, fileName).then(data => {
        targetDiv.remove()
    })
}

// 특정 파일 삭제
async function removeFileToServer(uuid, fileName) {

    const response = await axios.delet(`/remove/${uuid}_${fileName}`)

    return response.data
}


// 업로드 모달
