/*
 * Copyright (c) 2006-2007, AIOTrade Computing Co. and Contributors
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *    
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *    
 *  o Neither the name of AIOTrade Computing Co. nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.aiotrade.platform.modules.netbeans.ui.actions;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import org.aiotrade.lib.charting.view.ChartViewContainer;
import org.aiotrade.platform.modules.netbeans.ui.windows.AnalysisChartTopComponent;
import org.aiotrade.platform.modules.netbeans.ui.windows.RealTimeChartTopComponent;
import org.openide.util.HelpCtx;
import org.openide.util.Utilities;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;


/**
 *
 * @author Caoyuan Deng
 */
object SwitchCalendarTradingTimeViewAction {

  private var toggleButton: JToggleButton = _

  def updateToolbar(selectedViewContainer: ChartViewContainer) {
    val selected = selectedViewContainer.controller.isOnCalendarMode
    toggleButton.setSelected(selected)
  }




}
class SwitchCalendarTradingTimeViewAction extends CallableSystemAction {
  import SwitchCalendarTradingTimeViewAction._

  def performAction {
    java.awt.EventQueue.invokeLater(new Runnable() {
        def run {
          if (toggleButton.isSelected) {
            toggleButton.setSelected(false)
          } else {
            toggleButton.setSelected(true)
          }
        }
      })
  }
    
  def getName: String = {
    "Calendar/Trading date View"
  }
    
    
  def getHelpCtx: HelpCtx = {
    HelpCtx.DEFAULT_HELP
  }
    
  override protected def iconResource: String = {
    "org/aiotrade/platform/core/ui/netbeans/resources/naturalTrading.gif"
  }
    
  override protected def asynchronous: Boolean = {
    false
  }
    
  override def getToolbarPresenter: Component = {
    val iconImage = Utilities.loadImage("org/aiotrade/platform/core/ui/netbeans/resources/naturalTrading.gif");
    val icon = new ImageIcon(iconImage);
        
    toggleButton = new JToggleButton();
    toggleButton.setIcon(icon);
    toggleButton.setToolTipText("Calendar/Trading date View");
        
    toggleButton.addItemListener(new ItemListener() {
        def itemStateChanged(e: ItemEvent) {
          val state = e.getStateChange();
                
          val tc = WindowManager.getDefault.getRegistry.getActivated
          val viewContainer = tc match {
            case x: AnalysisChartTopComponent => x.getSelectedViewContainer
            case x: RealTimeChartTopComponent => x.getViewContainer
            case null => null
            case _ =>
              JOptionPane.showMessageDialog(WindowManager.getDefault.getMainWindow, "Please select a view by clicking on it first!")
              null
          }
                
          if (state == ItemEvent.SELECTED) {
            if (viewContainer != null && !viewContainer.controller.isOnCalendarMode) {
              viewContainer.controller.isOnCalendarMode = true
            }
          } else {
            if (viewContainer != null && viewContainer.controller.isOnCalendarMode) {
              viewContainer.controller.isOnCalendarMode = false
            }
          }
        }
      })
        
    toggleButton
  }
    
}

