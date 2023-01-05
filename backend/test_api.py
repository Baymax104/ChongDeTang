import requests


def register_test():
    url = "http://localhost:3000/api/user/account/register/"
    d = {"username": "clb",
         "password": "clb030108",
         "confirmPassword": "clb030108",
         "mail": "1796390642@qq.com",
         "phone": "18056199338"}
    res = requests.post(url, d)
    print(res.reason)


def login_test():
    url = "http://localhost:3000/api/user/account/token/"
    d = {"username": "baymax",
         "password": "123456"}

    res = requests.post(url, d)
    print(res.reason)
    res = res.text
    token = eval(res)['token']
    return token


def getinfo_test(token):
    url = "http://localhost:3000/api/user/account/info/"
    headers = {
        "Authorization": "Bearer " + token,
    }
    res = requests.get(url, headers=headers)
    value = eval(res.text)
    print(value)


if __name__ == '__main__':
#     getinfo_test(login_test())
    register_test()