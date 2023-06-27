const localhost = 'http://localhost:8000'

const reqBody = {
  "shopName": "Shop 1",
}


const serverCall = fetch(`${localhost}/admin/get_shop_details`, {
  method: 'POST',
  mode: 'no-cors',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(reqBody),
}).then((res) => {
  return res.json()
}).then((data) => {
  console.log(data)
});