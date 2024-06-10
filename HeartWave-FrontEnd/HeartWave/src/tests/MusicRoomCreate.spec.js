import { mount } from '@vue/test-utils';
import MusicRoom from './MusicRoom.vue';

describe('MusicRoom.vue', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(MusicRoom, {
      data() {
        return {
          newMusicRoom: {
            name: '',
            intro: '',
            auth: '',
            avatar: '',
            friends: []
          },
          newMusicRoomDialog: false
        };
      }
    });
  });

  const testCases = [
    {
      id: 'TC1',
      data: {
        name: 'Normal Music Room',
        intro: 'This is a test music room description.',
        auth: 'public',
        avatar: '',
        friends: []
      },
      description: 'Valid Test Case 1',
      expected: true
    },
    {
      id: 'TC2',
      data: {
        name: 'Another Room',
        intro: 'A short music room description.',
        auth: 'private',
        avatar: {name: 'avatar.png', size: 1 * 1024 * 1024, type: 'image/png'},
        friends: [1, 2, 3, 4, 5]
      },
      description: 'Valid Test Case 2',
      expected: true
    },
    {
      id: 'TC3',
      data: {
        name: 'Music Room Name with a Maximum of Fifty Characters',
        intro: 'The description can be llong, up to 50 characters.',
        auth: 'public',
        avatar: {name: 'avatar.png', size: 1.5 * 1024 * 1024, type: 'image/png'},
        friends: [1, 2, 3, 4, 5, 6]
      },
      description: 'Valid Test Case 3',
      expected: true
    },
    {
      id: 'TC4',
      data: {
        name: "TangciuYeung's Afterschool Private Relax MusicRoom",
        intro: "Here is a test description, contain, such as [];'.",
        auth: 'private',
        avatar: '',
        friends: [1, 2, 3, 4, 5, 6, 7, 8, 9]
      },
      description: 'Valid Test Case 4',
      expected: true
    },
    {
      id: 'TC5',
      data: {
        name: 'Short Name',
        intro: 'Here is a shorter description.',
        auth: 'public',
        avatar: {name: 'avatar.png', size: 500 * 1024, type: 'image/png'},
        friends: []
      },
      description: 'Valid Test Case 5',
      expected: true
    },
    {
      id: 'ITC1',
      data: {
        name: '',
        intro: '正常的音乐室简介。',
        auth: 'public',
        avatar: {name: 'avatar.png', size: 1 * 1024 * 1024, type: 'image/png'},
        friends: ['friend']
      },
      description: 'Invalid: Music room name is empty',
      expected: false
    },
    {
      id: 'ITC2',
      data: {
        name: "Music Room Name Longer Than Fifty Charactersbalabalabalabala..." +
                "Music Room Name Longer Than Fifty Charactersbalabalabalabala...",
        intro: 'A short music room description.',
        auth: 'private',
        avatar: {name: 'avatar.png', size: 1.5 * 1024 * 1024, type: 'image/png'},
        friends: [1, 2]
      },
      description: 'Invalid: Music room name exceeds 50 characters',
      expected: false
    },
    {
      id: 'ITC3',
      data: {
        name: 'MyMusicRoom@!',
        intro: 'Here is a normal length description.',
        auth: 'public',
        avatar: '',
        friends: [1, 2, 3]
      },
      description: 'Invalid: Music room name contains illegal characters',
      expected: false
    },
    {
      id: 'ITC4',
      data: {
        name: 'normal music room',
        intro: '1'.repeat(51),
        auth: 'public',
        avatar: {name: 'avatar.png', size: 2 * 1024 * 1024, type: 'image/png'},
        friends: [1, 2, 3, 4]
      },
      description: 'Invalid: Music room description exceeds 50 characters',
      expected: false
    },
    {
      id: 'ITC5',
      data: {
        name: 'Normal Room',
        intro: 'Description contains illegal characters @#$.',
        auth: 'private',
        avatar: {name: 'avatar.png', size: 500 * 1024, type: 'image/png'},
        friends: [1, 2 ,3, 4, 5]
      },
      description: 'Invalid: Music room description contains illegal characters',
      expected: false
    },
    {
      id: 'ITC6',
      data: {
        name: 'Music Room',
        intro: 'Normal music room description.',
        auth: '',
        avatar: '',
        friends: [1, 2, 3, 4, 5, 6]
      },
      description: 'Invalid: Access permission not selected',
      expected: false
    },
    {
      id: 'ITC7',
      data: {
        name: 'Music Room',
        intro: 'This is a test music room description.',
        auth: 'private',
        avatar: {name: 'avatar.png', size: 1.5 * 1024 * 1024, type: 'image/png'},
        friends: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
      },
      description: 'Invalid: Number of invited friends exceeds 10',
      expected: false
    },
    {
      id: 'ITC8',
      data: {
        name: 'Music Room',
        intro: 'This is a test music room description.',
        auth: 'public',
        avatar: {name: 'avatar.png', size: 1 * 1024 * 1024, type: 'image/png'},
        friends: ['nonexistentFriend']
      },
      description: 'Invalid: Selected non-existent friend',
      expected: false
    },
    {
      id: 'ITC9',
      data: {
        name: 'Music Room',
        intro: 'This is a test music room description.',
        auth: 'private',
        avatar: {name: 'avatar.gif', size: 1.5 * 1024 * 1024, type: 'image/png'},
        friends: [1]
      },
      description: 'Invalid: Upload non-jpg/png format image',
      expected: false
    },
    {
      id: 'ITC10',
      data: {
        name: 'Music Room',
        intro: 'This is a test music room description.',
        auth: 'public',
        avatar: {name: 'avatar.png', size: 3 * 1024 * 1024, type: 'image/png'},
        friends: [1, 2, 3, 4, 5]
      },
      description: 'Invalid: Upload image exceeds 2MB size',
      expected: false
    },
    {
      id: 'ITC11',
      data: {
        name: 'Music Room',
        intro: 'This is a test music room description.',
        auth: 'private',
        avatar: {name: 'avatar.txt', size: 500 * 1024, type: 'image/png'},
        friends: []
      },
      description: 'Invalid: Upload invalid file (e.g., text file)',
      expected: false
    },
  ];

  testCases.forEach(tc => {
    it(`${tc.id} - ${tc.description}`, async () => {
      await wrapper.setData({ newMusicRoom: tc.data });
      expect(wrapper.vm.validateForm()).toBe(tc.expected);
    });
  });
});
