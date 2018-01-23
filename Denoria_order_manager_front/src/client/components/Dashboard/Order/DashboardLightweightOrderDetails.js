import React, { Component } from 'react';
import { Label, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

import '../../../assets/css/LightweightOrderDetails.css';

/**
 * Only used by DashboardOrderList
 * Called to show user's orders on his dashboard in lightweight format
 */
class DashboardLightweightOrderDetails extends Component{
    render(){
        return(
          <div className="lightweight-order-details col-xs-12 col-sm-12-col-md-12 col-lg-12">
              <div className="col-xs-6">
                  <p>{this.props.order.title}</p>
              </div>
              <div className="col-xs-3">
                  <Label bsStyle="success">{this.props.order.orderStatus}</Label>
              </div>
              <div className="col-xs-3">
                  <Link to={"/profil/" + localStorage.getItem("user-pseudo") + "/" + this.props.order.id}><Button bsStyle="info">Afficher</Button></Link>
              </div>
          </div>
        );
    }
}
export default DashboardLightweightOrderDetails;