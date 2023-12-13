
const tokens = {
  admin: {
    token: 'admin-token'
  },
  editor: {
    token: 'editor-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  'editor-token': {
    roles: ['editor'],
    introduction: 'I am an editor',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}

module.exports = [
  // user login
  {
    url: '/vue-admin-template/user/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = username + '-token'

      return {
        code: 20000,
        data: { token: token }
      }
      // mock error
      // if (!token) {
      //   return {
      //     code: 60204,
      //     message: 'Account and password are incorrect.'
      //   }
      // }

      // return {
      //   code: 20000,
      //   data: token
      // }
    }
  },
  // get user info
  {
    url: '/vue-admin-template/user/info\.*',
    type: 'get',
    response: config => {
      const { token } = config.query;
      // 从 token 中提取用户名
      const username = token.split('-')[0];
      
      // 创建假的用户信息
      const info = {
        roles: ['user'], // 或根据 username 定制角色
        introduction: `I am ${username}`,
        avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
        name: username
      };

      return {
        code: 20000,
        data: info
      };
    }
  },

  // // get user info
  // {
  //   url: '/vue-admin-template/user/info\.*',
  //   type: 'get',
  //   response: config => {
  //     const { token } = config.query
  //     const info = users[token]

  //     // mock error
  //     if (!info) {
  //       return {
  //         code: 50008,
  //         message: 'Login failed, unable to get user details.'
  //       }
  //     }

  //     return {
  //       code: 20000,
  //       data: info
  //     }
  //   }
  // },

  // user logout
  {
    url: '/vue-admin-template/user/logout',
    type: 'post',
    response: _ => {
      return {
        code: 20000,
        data: 'success'
      }
    }
  }
]
