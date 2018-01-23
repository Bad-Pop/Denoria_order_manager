import React, {Component} from 'react';
import { Alert } from 'react-bootstrap';
import DashboardLightweightOrderDetails from './DashboardLightweightOrderDetails';

import '../../../assets/css/DashboardOrderList.css';

class DashboardOrderList extends Component {

    render() {
        return (
            <div className="col-xs-12 col-sm-12 col-md-9 col-lg-9">
                <h3>Liste de mes commandes</h3>
                <hr className="page-hr"/>
                {
                    this.props.orders.length > 0
                    ?
                        <div>
                            {
                                this.props.orders.map((order) =>
                                <DashboardLightweightOrderDetails key={order.id} order={order}/>
                                )
                            }
                        </div>
                    :
                        <Alert bsStyle="warning">
                            <p>Vous n'avez passé aucune commande.</p>
                        </Alert>
                }
            </div>
        );
    }
}

export default DashboardOrderList;