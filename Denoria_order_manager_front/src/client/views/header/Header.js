import React, {Component} from 'react';
import { Navbar } from 'react-bootstrap';
import HeaderLinks from './HeaderLinks';

class Header extends Component {

    constructor(props){
        super(props);
        this.mobileSidebarToggle = this.mobileSidebarToggle.bind(this);
        this.state = {
            sidebarExists: false
        };
    }

    mobileSidebarToggle(e){
        if(this.state.sidebarExists === false){
            this.setState({
                sidebarExists : true
            });

        }
        e.preventDefault();
        document.documentElement.classList.toggle('nav-open');
        const node = document.createElement('div');
        node.id = 'bodyClick';
        node.onclick = function(){
            this.parentElement.removeChild(this);
            document.documentElement.classList.toggle('nav-open');
        };
        document.body.appendChild(node);
    }

    render() {
        return (
            <div>
                <Navbar fluid fixedTop={true}>
                    <Navbar.Header>
                        <Navbar.Toggle onClick={this.mobileSidebarToggle}/>
                    </Navbar.Header>
                    <Navbar.Collapse id="header_navbar">
                        <HeaderLinks/>
                    </Navbar.Collapse>
                </Navbar>
            </div>
        );
    }
}

export default Header;