import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DccCommand } from './dcc-command'

@Component({
    selector: 'app-dcc',
    templateUrl: './dcc.component.html',
    styleUrls: ['./dcc.component.css']
})
export class DccComponent implements OnInit {

    dccCommand: DccCommand;

    /**
     * Custom constructor.
     */
    constructor(
        private http: HttpClient
    ) {
        this.dccCommand = new DccCommand()
    }

    ngOnInit() {
    }

    /**
     * Execute the DCC Command
     */
    executeCommand() {
        if (this.dccCommand.command != null && this.dccCommand.command != "") {

            // post
            this.http.post('/dcc', this.dccCommand)
                .subscribe(
                    data => console.log("Success: " + data),
                    error => console.log("Error: " + error)
                )
        }
    }

    switchOn() {
        this.http.post('/dcc/on', this.dccCommand)
            .subscribe(
                data => console.log("Success: " + data),
                error => console.log("Error: " + error)
            )
    }

    switchOff() {
        this.http.post('/dcc/off', this.dccCommand)
            .subscribe(
                data => console.log("Success: " + data),
                error => console.log("Error: " + error)
            )
    }
}
