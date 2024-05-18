import React, {Component, useMemo} from "react";
import MOCK_DATA from "./MOCK_DATA.json";
import styles from './Dashboard.module.scss';
import {formatNumberWithSpaces} from "../../utils/Common";

function DashboardPage() {

    const data = useMemo(() => MOCK_DATA, []);

    return (
        <div className={styles.container}>
            {data.map((item) => <Card title={item.title}
                                      count={item.count}/>)}
        </div>
    )

}

class Card extends Component {

    render() {
        return (
            <div className={styles.card}>
                <p>{formatNumberWithSpaces(this.props.count)}</p>
                <span>{this.props.title}</span>
            </div>
        )
    }
}

export default DashboardPage