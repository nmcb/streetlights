/*
 * Proof of concept depicting a restful specification of access to
 * infrastructure related data graphs.
 *
 * Copyright (C) 2012 NMCB B.V.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

package streetlights.model.infra;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Borst
 * @since 15/05/12
 */
// @XmlRootElement(name = "roads")
@XmlRootElement(name = "roads")
public class Roads
{
  @XmlElement(name = "road")
  private List<Road> roads;

  public Roads() {
  }

  public Roads(List<Road> roads) {
     this.roads = new ArrayList<Road>(roads);
  }

  public List<Road> list() {
    return roads;
  }
}
