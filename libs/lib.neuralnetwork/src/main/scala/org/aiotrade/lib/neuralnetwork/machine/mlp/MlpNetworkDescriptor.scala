/*
 * Copyright (c) 2006-2011, AIOTrade Computing Co. and Contributors
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

package org.aiotrade.lib.neuralnetwork.machine.mlp

import org.aiotrade.lib.collection.ArrayList
import org.aiotrade.lib.neuralnetwork.core.descriptor.NetworkDescriptor

/**
 * 
 * @author Caoyuan Deng
 */
class MlpNetworkDescriptor extends NetworkDescriptor {
    
  private var _layerDescriptors = new ArrayList[MlpLayerDescriptor]
    
  def numLayers = _layerDescriptors.length
    
  def addHiddenLayerDescriptor(le: MlpLayerDescriptor) {
    _layerDescriptors += le
  }
    
  def layerDescriptors = _layerDescriptors
  def layerDescriptors_=(layerDescriptors: ArrayList[MlpLayerDescriptor]) {
    _layerDescriptors = layerDescriptors
  }

  @throws(classOf[Exception])
  protected def checkValidation() {
    for (layerDescriptor <- _layerDescriptors) {
      if (layerDescriptor.numNeurons < 1) {
        throw new Exception(layerDescriptor.toString)
      }
            
      val neuronClass = try {
        Class.forName(layerDescriptor.neuronClassName)
      } catch {
        case ex: ClassNotFoundException => throw new Exception(layerDescriptor.toString)
      }
    }

    if (_layerDescriptors.length == 0) {
      throw new Exception("no layers defined")
    }
        
    val param = arg.asInstanceOf[MlpNetwork.Arg]
    if (param.learningRate < 0) {
      throw new Exception("learning rate must > 0")
    }
    if (param.maxEpoch < 0) {
      throw new Exception("max epoch must be > 0")
    }
    if (param.predictionError <= 0) {
      throw new Exception("prediction error must > 0")
    }
  }
    
  def serviceClass = classOf[MlpNetwork]
}