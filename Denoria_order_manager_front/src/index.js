//  _____                        _       _     _        ___  _           _         _   _            _
// /  __ \                      (_)     | |   | |      / _ \| |         (_)       | | | |          | |                 | |
// | /  \/ ___  _ __  _   _ _ __ _  __ _| |__ | |_    / /_\ \ | _____  ___ ___    | | | | __ _  ___| |__   __ _ _ __ __| |
// | |    / _ \| '_ \| | | | '__| |/ _` | '_ \| __|   |  _  | |/ _ \ \/ / / __|   | | | |/ _` |/ __| '_ \ / _` | '__/ _` |
// | \__/\ (_) | |_) | |_| | |  | | (_| | | | | |_    | | | | |  __/>  <| \__ \   \ \_/ / (_| | (__| | | | (_| | | | (_| |
//  \____/\___/| .__/ \__, |_|  |_|\__, |_| |_|\__|   \_| |_/_|\___/_/\_\_|___/    \___/ \__,_|\___|_| |_|\__,_|_|  \__,_|
//             | |     __/ |        __/ |
//             |_|    |___/        |___/

import React from 'react';
import ReactDOM from 'react-dom';
import App from './containers/App/App';

import registerServiceWorker from './registerServiceWorker';

ReactDOM.render((<App/>),document.getElementById('root'));

registerServiceWorker();